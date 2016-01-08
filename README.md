# vaadin-scala-akka-sbt-template
An SBT built VAADIN 7, Scala and AKKA template web ui.

[![Build Status](https://travis-ci.org/mcsherrylabs/vaadin-scala-akka-sbt-template.svg?branch=master)](https://travis-ci.org/mcsherrylabs/vaadin-scala-akka-sbt-template)

This is an experimental hooking up of AKKA actors to the VAADIN callback event system.
  
The template application consists of a login view and a main view, the main view further consists of a data entry left 
panel and a text area on the right hand side for feedback.
 
The text area on the right is registered for all events in the category 'sss.ui.template.feedback' and click events from the 'Clear' button on top of it. 
 
```
 // Create a uiReactor, one per VAADIN UI
 val uiReactor = UIReactor(getUI)
 
  //Add the button click events to the uiReactor
 val resetBtn = new Button("Clear Panel")
 resetBtn.addClickListener(uiReactor)
 
 // Create a text area reactor actor.
 // Note that the components the actor is insterested in are listed - in this case resetBtn ...    
 val textAreaActor = uiReactor actorOf (Props(TextAreaReactor), resetBtn)
 
 // Another way to do this is
 //val textAreaActor = uiReactor actorOf (Props(TextAreaReactor))
 //resetBtn ! ListenTo(resetBtn)
 
 // Register the actor for feedback events
 textAreaActor ! Register("sss.ui.template.feedback")
```

Now the actor instance will receive any "sss.ui.template.feedback" events from anywhere and any clicks from resetBtn ...
   
```
object TextAreaReactor extends UIEventActor {
   override def react(reactor: ActorRef, broadcaster: ActorRef, ui: UI): Receive = {

     case ComponentEvent(_, ev: Button.ClickEvent) => push(textArea.setValue(""))
     case Feedback(msg) => push(textArea.setValue(s"${textArea.getValue}\n${msg}"))
   }
}
```
Because the actor is completely reactive and not bound to any browser request, any updates to the UI must be 'pushed' as per VAADIN 7 push.
 
``` sbt run ``` will run the server at ```localhost:8080/template```
  
There is also a command line tool to further demonstrate the broadcast capability.

``` sbt "test:run  logout OUT!"``` will send an AKKA message to the server which the broadcasts a logout category message to anything registered for that category.

In this case the LogoutListenerActor is registered for that category and will react to it by logging out all users.

``` sbt "test:run  msg Hello!"``` is a "sss.ui.template.feedback" category event and will appear on the text area.
 
