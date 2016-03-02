package controllers

import com.google.inject.Inject
import models.UserServiceApi
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.Play.current
import play.api.i18n.Messages.Implicits._
import play.api.mvc.Flash

case class UserData(email:String,password:String)

class Login @Inject()(user:UserServiceApi) extends Controller{

  val getList=user.getUser
//  def validate(name:String,age: Int) = {
//    age match {
//      case age if age <= 50 =>
//        Some(UserData(name, age))
//      case _ =>
//        None
//    }
//  }
  def validate(email:String):Boolean={
   getList.map(_.email).contains(email)
  }

  val userForm = Form(
    mapping(
      "email"->email.verifying("Failed from Constraint",field=>validate(field)),
      "password"->nonEmptyText
    )(UserData.apply)(UserData.unapply)
//      verifying("Failed form constraints!", fields => fields match {
//      case userData => validate(userData.name, userData.age).isDefined
  //  })
  )

  def getLoad =Action {implicit request=>
    Ok(views.html.login(userForm))
  }

    def log =Action { implicit request =>
      userForm.bindFromRequest.fold(
        formWithErrors => {
          //Ok("failure")
        // BadRequest(views.html.login(formWithErrors))
          //BadRequest.flashing("error"->"Incorrect")
          Redirect(routes.Login.getLoad()).flashing("error" -> "Incorrect Username Or Password")
        },
        userData => {
          if(getList.map(_.email).contains(userData.email) && getList.map(_.password).contains(userData.password)) {
            Redirect(routes.Login.account()).withSession("email" -> userData.email).flashing("success" -> "Successfully logged in!")
          }
          else
            Redirect(routes.Login.getLoad()).flashing("error"->"Incorrect Username Or Password")
        }
      )
       }

      def account=Action{request=>
        request.session.get("email").map{email=>Ok(views.html.account(email))}.getOrElse{
          Unauthorized("You are not Logged In.")
        }

      }


    def logout=Action{implicit request=>
      Redirect(routes.Login.getLoad()).withNewSession

    }

  def forgotpassword=Action{implicit request=>
    Ok(views.html.forgetpassword())

  }


}
