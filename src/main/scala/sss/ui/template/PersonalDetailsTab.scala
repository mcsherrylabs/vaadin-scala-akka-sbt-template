package sss.ui.template

import akka.actor.{ ActorRef, Props }
import com.vaadin.data.Property
import com.vaadin.ui._
import sss.ui.reactor._

import scala.language.implicitConversions
import scala.util.{ Failure, Success, Try }

/**
 * Created by alan on 12/10/15.
 */
class PersonalDetailsTab(uiReactor: UIReactor) extends VerticalLayout with SkeletonForm {

  val today = new DateField("Today is")
  val dob = new DateField("Date of Birth")
  val salary = new TextField("Salary")

  setCaption("Personal Details")

  val calcBtn = footer
  calcBtn.addClickListener(uiReactor)
  salary.addValueChangeListener(uiReactor)
  dob.addValueChangeListener(uiReactor)
  today.addValueChangeListener(uiReactor)

  uiReactor.actorOf(Props(PersonalDetailsActor), calcBtn, salary, dob, today)
  tabContent.addComponents(dob, salary, today)

  object PersonalDetailsActor extends UIEventActor {
    override def react(reactor: ActorRef, broadcaster: ActorRef, ui: UI): Receive = {

      case ComponentEvent(`calcBtn`, _: Button.ClickEvent) =>

        Try {

          broadcaster ! Feedback(s"DOB ${dob.getValue}")
          broadcaster ! Feedback(s"Today ${today.getValue}")
          broadcaster ! Feedback(s"Salary ${salary.getValue}")

        } match {
          case Failure(e) => {
            broadcaster ! Feedback(s"Error, check input values")
            broadcaster ! Feedback(s"$e")
          }
          case Success(_) => // Success requires no action
        }
      case ComponentEvent(`salary`, ev: Property.ValueChangeEvent) => broadcaster ! Feedback(s"Salary now ${ev.getProperty.getValue}")
      case ComponentEvent(`dob`, ev: Property.ValueChangeEvent) => broadcaster ! Feedback(s"Dob now ${ev.getProperty.getValue}")
      case ComponentEvent(`today`, ev: Property.ValueChangeEvent) => broadcaster ! Feedback(s"Today now ${ev.getProperty.getValue}")

    }

  }

}

