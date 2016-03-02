package models

import com.google.inject.ImplementedBy
import scala.collection.mutable.ListBuffer

case class User(email:String,password:String)


@ImplementedBy(classOf[UserService])
trait UserServiceApi {
   def getUser:ListBuffer[User]
  def modifyUser(email:String,password:String,newpassword:String):Boolean
  def createUser(email:String,password:String):Unit
  def modifyEmail(email:String,newemail:String,password:String):Unit
}

class UserService extends UserServiceApi{
  val userList:ListBuffer[User]=ListBuffer(User("pallavi@knoldus.in","pallavi"),User("himani@knoldus.in","himani"));

  def getUser:ListBuffer[User]={
    println("Coming here")
    userList

  }

  def modifyUser(email:String,password:String,newpassword:String):Boolean={
    if(userList.map(_.email).contains(email)) {
      userList.update(userList.map(_.email).indexOf(email),User(email,newpassword))
      true
    }
    false
  }

  def modifyEmail(email:String,newemail:String,password:String)={
    if(userList.map(_.email).contains(email)) {

      userList.update(userList.map(_.email).indexOf(email),User(newemail,password))
    }
  }


  def createUser(email:String,password:String)={
    if(!userList.map(_.email).contains(email)) {

      userList.append(User(email,password))
    }
  }

}
