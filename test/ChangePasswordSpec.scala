import controllers.{ChangePassword, SignUp}

import scala.collection.mutable.ListBuffer
import models.{User, UserServiceApi, UserService}
import org.specs2.mock.Mockito
import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import play.api.test._
import play.api.test.Helpers._
import org.mockito.Mockito._


class ChangePasswordSpec extends PlaySpecification with Mockito {

  val mockedUserService = mock[UserServiceApi]
  val testCtrl = new ChangePassword(mockedUserService)

  "Appl" should {

    "modigy" in new WithApplication() {

      when(mockedUserService.modifyUser("","","")).thenReturn(true)

      val result = testCtrl.putPass.apply(FakeRequest(POST, "/putPass").withFormUrlEncodedBody())
      status(result) must equalTo(SEE_OTHER)
      /*val service = mock[UserServiceApi]
      val testController = new SignUp(service)

      when(service.getUser).thenReturn(ListBuffer(User("Nil", "Nil")))
      //Stubbing

      //val res=call(controller.getUser,(FakeRequest(GET,"/getUser")))

      val logRoute = route(FakeRequest(GET, "/")).get
      status(logRoute) must equalTo(SEE_OTHER)
*/
    }

    "Empty change password form rendering" in new WithApplication() {

      val logRoute = route(FakeRequest(GET, "/getPass")).get
      status(logRoute) must equalTo(OK)
    }

  }

}
