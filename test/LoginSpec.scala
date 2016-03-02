import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

@RunWith(classOf[JUnitRunner])
class LoginSpec extends Specification{

  "Login" should {



    "render the login page " in new WithApplication() {
      val logRoute=route(FakeRequest(GET,"/getLoad")).get

      contentType(logRoute) must beSome.which(_ == "text/html")
      status(logRoute) must equalTo(OK)
    }

    "Not able to authenticate user due to a bad request" in new WithApplication() {
      val logRoute=route(FakeRequest(POST,"/submit")).get
      status(logRoute) must equalTo(SEE_OTHER)

    }

    "Authenticating user when correct credentials are given" in new WithApplication() {
      val logRoute=route(FakeRequest(POST,"/submit").withFormUrlEncodedBody("email"->"himani@knoldus.in","password"->"himani")).get
      status(logRoute) must equalTo(SEE_OTHER)

    }

    "Authenticating user when incorrect credentials are given" in new WithApplication() {
      val logRoute=route(FakeRequest(POST,"/submit").withFormUrlEncodedBody("email"->"Ram@knol.in","password"->"ab")).get
      status(logRoute) must equalTo(SEE_OTHER)

    }
  }

     "Fetching account details if the user is logged in" in new WithApplication() {

       val logRoute=route(FakeRequest(GET,"/account").withSession("email"->"pallavi@knoldus.in")).get
       status(logRoute) must equalTo(OK)
     }

  "Fetching account details when the user is not logged in" in new WithApplication() {

    val logRoute=route(FakeRequest(GET,"/account")).get
    status(logRoute) must equalTo(UNAUTHORIZED)
  }

    "Logging out of the application" in new WithApplication() {
      val logRoute=route(FakeRequest(GET,"/logout")).get
      status(logRoute) must equalTo(SEE_OTHER)

    }

  "Rendering the forgot password link" in new WithApplication() {
    val logRoute=route(FakeRequest(GET,"/forgotpassword")).get
    status(logRoute) must equalTo(OK)

  }

}
