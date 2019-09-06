Code contains some extra files that got created as part of bootstrapping the template project. Here are the files that i worked on 

```build.sbt``` : Just adding needed dependency. Its equivalent of rake + Gemfile in RoR. 

```conf/routes``` : Contains the api routes and their bindings to controllers.

```app/controllers/ConstructionProjectController.scala``` : contains the logic for handling incoming api requests and authenticating the requests. 

```app/services/ConstructionProjectService.scala``` : contains the business logic i.e creating, retrieving and deleting resources.

```app/model/ConstructionProject.scala```: contains model classes. 
