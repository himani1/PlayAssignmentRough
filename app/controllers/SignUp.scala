package controllers

import com.google.inject.Inject
import models.UserServiceApi
import play.api.mvc.Controller
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.Play.current
import play.api.i18n.Messages.Implicits._

case class Contact(email:String,password:String,repeatpassword:String)
class SignUp @Inject()(user:UserServiceApi) extends Controller {

  val contactForm = Form(
    mapping(
      "email" -> email,
      "password" -> nonEmptyText,
      "repeatpassword" -> nonEmptyText
    )(Contact.apply)(Contact.unapply)
  )


  def sign = Action { implicit request =>
    Ok(views.html.signup(contactForm))
  }

  def  submit =Action { implicit request =>
    contactForm.bindFromRequest.fold(
      formWithErrors => {
        Redirect(routes.SignUp.sign()).flashing("error" -> "Unable to create user, Try again")
      },
      userData => {
        if(userData.password==userData.repeatpassword) {
          user.createUser(userData.email,userData.password)
          //Redirect(routes.Login.getLoad())
          Redirect(routes.Login.account()).withSession("email"->userData.email)
        }
        else
          Redirect(routes.SignUp.sign()).flashing("error" -> "Repeat Passwords doesnot Match Password")
      }
    )
  }
}