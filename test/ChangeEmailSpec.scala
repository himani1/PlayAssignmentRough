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


class ChangeEmailSpec extends Specification with Mockito {

  "get new Email" in new WithApplication() {

    val service=mock[UserServiceApi]
    val controller=new SignUp(service)

    when(service.getUser).thenReturn(ListBuffer(User("Nil","Nil")))   //Stubbing

    val logRoute=route(FakeRequest(POST,"/putEmail")).get
    status(logRoute) must equalTo(SEE_OTHER)

  }

  "Empty change email form rendering" in new WithApplication() {

    val logRoute=route(FakeRequest(GET,"/getEmail")).get
    status(logRoute) must equalTo(OK)
  }

}

