import models.{User, UserService}
import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

import scala.collection.mutable.ListBuffer

@RunWith(classOf[JUnitRunner])
class UserServiceSpec extends Specification{


  "render the login page " in new WithApplication() {
    val obj=new UserService()

    (obj.getUser) must equalTo(ListBuffer(User("pallavi@knoldus.in","pallavi"),User("himani@knoldus.in","himani")))
  }

}
