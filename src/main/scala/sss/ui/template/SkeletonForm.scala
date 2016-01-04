package sss.ui.template

import com.vaadin.ui.{ Alignment, Button, FormLayout, VerticalLayout }

/**
 * Created by alan on 12/10/15.
 */
trait SkeletonForm {

  self: VerticalLayout =>

  setMargin(true)
  val tabContent = new FormLayout
  addComponent(tabContent)

  def footer: Button = {
    val footer = new VerticalLayout
    val calcBtn = new Button("Calc")

    footer.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT)
    footer.addComponent(calcBtn)
    addComponent(footer)
    calcBtn
  }

}