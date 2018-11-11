jQuery(function($) {
	$( "#header" ).load( "components/appbar.html" );
	$( "#sidedrawer" ).load( "components/sidemenu.html" );
	$( "#content" ).load( "pages/login.html" );
});

function loadPage(pageName) {
	$("#content").load("../pages/" + pageName + ".html");
}