import controllers.{SignUp}

import scala.collection.mutable.ListBuffer

import models.{User, UserServiceApi, UserService}
import org.specs2.mock.Mockito
import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._
import org.mockito.Mockito._

class SignUpSpec extends Specification with Mockito {

  "get user details" in new WithApplication() {

    val service=mock[UserServiceApi]
    val controller=new SignUp(service)

    when(service.getUser).thenReturn(ListBuffer(User("Nil","Nil")))   //Stubbing

    val logRoute=route(FakeRequest(POST,"/create")).get
    status(logRoute) must equalTo(SEE_OTHER)

  }

 "Empty sign up form rendering" in new WithApplication() {

   val logRoute=route(FakeRequest(GET,"/sign")).get
   status(logRoute) must equalTo(OK)
 }

}
