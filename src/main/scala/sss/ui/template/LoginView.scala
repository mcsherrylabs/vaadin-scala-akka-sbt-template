package sss.ui.template

import akka.actor.{ ActorRef, Props }
import com.vaadin.data.validator.AbstractValidator
import com.vaadin.navigator.View
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent
import com.vaadin.shared.ui.MarginInfo
import com.vaadin.ui._
import sss.ui.reactor._

case class KickedOut(msg: String) extends Event { val category = LoginView.category }
case class BroadcastMessage(msg: String) extends Event { val category = LoginView.category }

class LoginView(mainView: String, uiReactor: UIReactor) extends CustomComponent with View {

  setSizeFull

  private val user = new TextField("User:")
  private val password = new PasswordField("Password:")

  private val loginButton = new Button("Login")
  private val fields = new VerticalLayout(user, password, loginButton)

  user.setWidth("300px")
  user.setRequired(true)
  user.setInputPrompt("Your username ")

  password.setWidth("300px")
  password.addValidator(PasswordValidator)
  password.setRequired(true)
  password.setValue("")
  password.setNullRepresentation("")

  fields.setCaption("Please login (user/password1).... ")
  fields.setSpacing(true);
  fields.setMargin(new MarginInfo(true, true, true, false))
  fields.setSizeUndefined()

  val viewLayout = new VerticalLayout(fields)
  viewLayout.setSizeFull
  viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER)

  setCompositionRoot(viewLayout)

  loginButton.addClickListener(uiReactor)

  object LogoutListenerActor extends UIEventActor {
    override def react(reactor: ActorRef, broadcaster: ActorRef, ui: UI): Receive = {

      case ComponentEvent(`loginButton`, _) => push(login())
      case KickedOut(msg) => push(logout(msg))
      case BroadcastMessage(msg) => broadcaster ! Feedback(msg)
    }
  }

  val logoutListenerRef = uiReactor.actorOf(Props(LogoutListenerActor), loginButton)
  logoutListenerRef ! Register(LoginView.category)

  private def logout(msg: String): Unit = {

    UI.getCurrent.getSession.setAttribute("user", null)
    // Navigate to view
    UI.getCurrent.getNavigator.navigateTo(LoginView.name)
    Notification.show(msg)
  }

  private def login() {

    if (user.isValid() && password.isValid()) {

      val usernameStr = user.getValue();
      val passwordStr = this.password.getValue()

      val confUser = "user"
      val confPass = "password1"
      val isValid = usernameStr == confUser && passwordStr == confPass

      if (isValid) {
        // Store the current user in the service session
        getSession.setAttribute("user", usernameStr);
        // Navigate to main view
        getUI.getNavigator.navigateTo(mainView)

      } else {
        password.setValue(null)
        password.focus()
      }
    }
  }
  override def enter(viewChangeEvent: ViewChangeEvent): Unit = user.focus

  object PasswordValidator extends AbstractValidator[String]("The password provided is not valid") {

    protected def isValidValue(value: String) = {
      //
      // Password must be at least 8 characters long and contain at least
      // one number
      //
      if (value != null && (value.length() < 8 || !value.matches(".*\\d.*"))) false
      else true
    }

    def getType = classOf[String]
  }
}

object LoginView {
  val category = "sss.ui.LoginView"
  val name = "login"
}
