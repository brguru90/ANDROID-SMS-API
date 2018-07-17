<?php
	session_start();
	if (isset($_COOKIE['user']))
	{
		$_SESSION['user']=$_COOKIE['user'];
	}
	if (!isset($_SESSION['user']))
	{
		$_SESSION['location']= 'http://'.$_SERVER['HTTP_HOST'].$_SERVER['PHP_SELF'];//go to current page after login
		header('Location:login_form.php');
	}
	//print_r($_SESSION);
?>