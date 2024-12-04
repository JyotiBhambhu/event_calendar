//package com.jyoti.eventcalender.eventcalendar.navigation
//
//import androidx.compose.ui.layout.LayoutCoordinates
//import androidx.navigation.NavController
//import androidx.navigation.NavGraphBuilder
//import androidx.navigation.NavOptions
//import androidx.navigation.compose.composable
//import com.getir.designsystem.util.TrackScreenViewEvent
//import com.getir.gtCommonAndroid.analytics.FirebaseAnalytics.Companion.ScreenEvents.FA_SCREEN_CALENDAR
//import com.getir.gtCommonAndroid.spotlight.TargetScreen
//import com.jyoti.eventcalender.eventcalendar.CalendarRoute
//
//const val calendarNavigationRoute = "calendar_route"
//
//fun NavController.navigateToCalendar(navOptions: NavOptions? = null) {
//    this.navigate(calendarNavigationRoute, navOptions)
//}
//
//fun NavGraphBuilder.calendarScreen(
//    onCreateLeaveClick: (String?) -> Unit,
//    navigateToLogin: () -> Unit,
//    onLeaveClick: (String) -> Unit,
//    cordsUpdated: (TargetScreen, LayoutCoordinates) -> Unit = { _: TargetScreen, _: LayoutCoordinates -> },
//) {
//    composable(route = calendarNavigationRoute) {
//        CalendarRoute(
//            navigateToLogin = navigateToLogin,
//            onCreateLeaveClick = onCreateLeaveClick,
//            onLeaveClick = onLeaveClick,
//            cordsUpdated = cordsUpdated
//        )
//        TrackScreenViewEvent(screenName = FA_SCREEN_CALENDAR)
//    }
//}
