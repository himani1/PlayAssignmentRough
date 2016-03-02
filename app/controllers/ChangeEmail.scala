
package controllers

import com.google.inject.Inject
import models.UserServiceApi
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}
import play.api.i18n.Messages.Implicits._
import play.api.Play.current


case class mailData(oldemail:String,newemail:String,password: String)

class ChangeEmail @Inject()(user:UserServiceApi) extends Controller{


  val getList=user.getUser

  def validate(oldemail:String):Boolean={
    getList.map(_.email).contains(oldemail)
  }

  val mailForm = Form(
    mapping(
      "oldemail"->nonEmptyText.verifying("Failed from Constraint",field=>validate(field)),
      "newemail"->nonEmptyText,
      "password"->nonEmptyText
    )(mailData.apply)(mailData.unapply)
  )

  def getEmail =Action {implicit request=>
    Ok(views.html.changeemail(mailForm))
  }

  def  putEmail =Action { implicit request =>
    mailForm.bindFromRequest.fold(
      formWithErrors => {
        Redirect(routes.ChangeEmail.getEmail()).flashing("error" -> "Incorrect")
      },
      userData => {
        if(getList.map(_.email).contains(userData.newemail)) {
          val st=request.session.get("email").get
          user.modifyEmail(st,userData.newemail,userData.password)
          Redirect(routes.Login.logout())
        }
        else
          Redirect(routes.ChangeEmail.getEmail()).flashing("error" -> "Email Doesnot exists")
      }
    )
  }

}

