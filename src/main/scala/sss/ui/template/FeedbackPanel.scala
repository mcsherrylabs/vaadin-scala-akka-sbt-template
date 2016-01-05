package sss.ui.template

import akka.actor.{ ActorRef, Props }
import com.vaadin.ui._
import sss.ui.reactor._

case class Feedback(msg: String) extends Event { val category = FeedbackPanel.category }

object FeedbackPanel {

  val category = "sss.ui.template.feedback"

  def apply(uiReactor: UIReactor): Component = {

    val textArea = new TextArea
    val rhsLayout = new VerticalLayout
    rhsLayout.setSizeFull

    val resetBtn = new Button("Clear Panel", uiReactor)

    /*
    Or use ...
    val resetBtn = new Button("Clear Panel")
    resetBtn.addClickListener(uiReactor)
    */

    resetBtn.setWidth("100%")

    textArea.setWordwrap(true)
    textArea.setSizeFull

    rhsLayout.addComponent(resetBtn)
    rhsLayout.addComponent(textArea)
    rhsLayout.setExpandRatio(resetBtn, 0)
    rhsLayout.setExpandRatio(textArea, 1)

    object TextAreaReactor extends UIEventActor {
      override def react(reactor: ActorRef, broadcaster: ActorRef, ui: UI): Receive = {

        case ComponentEvent(_, ev: Button.ClickEvent) => push(textArea.setValue(""))
        case Feedback(msg) => push(textArea.setValue(s"${textArea.getValue}\n${msg}"))
      }
    }

    val textAreaActor = uiReactor actorOf (Props(TextAreaReactor), resetBtn)
    textAreaActor ! Register(FeedbackPanel.category)

    rhsLayout
  }
}
