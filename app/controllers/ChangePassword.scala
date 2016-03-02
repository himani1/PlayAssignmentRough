package controllers

import com.google.inject.Inject
import models.UserServiceApi
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}
import play.api.i18n.Messages.Implicits._
import play.api.Play.current


case class pastData(opass:String,fpass:String,rpass:String)

class ChangePassword @Inject()(user:UserServiceApi) extends Controller{

  val getList=user.getUser

  def validate(opass:String):Boolean={
    getList.map(_.password).contains(opass)
  }

  val passForm = Form(
    mapping(
      "opass"->nonEmptyText.verifying("Failed from Constraint",field=>validate(field)),
      "fpass"->nonEmptyText,
      "rpass"->nonEmptyText
    )(pastData.apply)(pastData.unapply)
  )

  def getPass =Action {implicit request=>
    Ok(views.html.changepassword(passForm))
  }

  def  putPass =Action { implicit request =>
    passForm.bindFromRequest.fold(
      formWithErrors => {
        Redirect(routes.ChangePassword.getPass()).flashing("error" -> "Incorrect")
      },
      userData => {
        if(userData.fpass==userData.rpass) {
          val st=request.session.get("email").get
            user.modifyUser(st,userData.opass,userData.fpass)
          Redirect(routes.Login.logout())
        }
        else
          Redirect(routes.ChangePassword.getPass()).flashing("error" -> "Repeat Passwords doesnot Match New Password")
      }
    )
  }

}
