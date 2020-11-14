package hu.bme.aut.untitledtemalab.demostuff

import hu.bme.aut.untitledtemalab.data.*

object DemoData {

    val demoJobList = mutableListOf<JobData>()
    val demoUserList = mutableListOf<UserData>()
    var loggedInUser = UserData(100000001, "Teszt Elek", "teszt@fejleszto.com", 5.9)

    init {
        var placeholderJobData = JobData(1, "Deliver 10 tons of Beer in the name of Democracy!", PackageSize.large,
                                        8500, "3308. 10. 28.", "3308. 10. 31.", 2)
        placeholderJobData.listingExpirationDate = "3308. 10. 30"
        var placeholderRouteData = RouteData("Mars Orbital", "Budapest, Schönherz kollégium")
        placeholderJobData.deliveryRoute = placeholderRouteData
        placeholderJobData.status = JobStatus.pending
        demoJobList.add(placeholderJobData)

        var placehoolderUserData = UserData(2, "Demo Dénes", "denes@demo.mrs", 6.6)
        demoUserList.add(placehoolderUserData)
    }
}