package sss.ui.template

import com.vaadin.navigator.View
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent
import com.vaadin.ui.{ Accordion, CustomComponent, HorizontalLayout }
import sss.ui.reactor.UIReactor

class MainView(uiReactor: UIReactor) extends CustomComponent with View {

  val name = ""

  override def enter(viewChangeEvent: ViewChangeEvent): Unit = Unit

  setSizeFull

  val baseLayout = new HorizontalLayout
  baseLayout.setMargin(true);
  baseLayout.setSizeFull

  val accordion = new Accordion
  baseLayout.addComponent(accordion)

  baseLayout.addComponent(FeedbackPanel(uiReactor))
  accordion.addComponent(new PersonalDetailsTab(uiReactor))
  setCompositionRoot(baseLayout)

}