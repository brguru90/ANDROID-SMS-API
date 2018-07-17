<?php

//setcookie("__test", "Guruprasad", time() + (86400 * 30 * 365), "/");
header('Content-Type: text/plain');
//header('Cookie:'.$_COOKIE['__test']); 
//ini_set('session.use_cookies', '0');
include('db.php');

// Create connection

$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection

if ($conn->connect_error)

{

    die("Connection failed: " . $conn->connect_error);

}
if(isset($_POST["user"]) && isset($_POST["pwd"]))
{
	$user=$_POST["user"];

	$pwd=$_POST["pwd"];

	$sql="select * from login where user='".$user."';";

	$login="invalid";

	$result=$conn->query($sql);

	if ($result->num_rows > 0) {

	    // output data of each row
      $login="invalid";

	    while($row = $result->fetch_assoc()) 

		{

		

			$passwd=$row["pwd"];

			if($pwd===$passwd && $row["user_type"]=="user")

				$login="valid";

		}

	}

	echo $login;
}
else
	echo "server side error";
$conn->close();

?>