<?php
include('db.php');
// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error)
{
    die("Connection failed: " . $conn->connect_error);
}
if(isset($_POST["uid"]) && isset($_POST["pwd"]) && isset($_POST["id"]))
{

	$flag=false;
	$uid=$_POST["uid"];

	$pwd=$_POST["pwd"];
	$id=$_POST["id"];

	$sql="select * from login where user='".$uid."';";

	$result=$conn->query($sql);

	if ($result->num_rows > 0) {

		$flag=false;

	    // output data of each row

	    while($row = $result->fetch_assoc()) 

		{

		

			$passwd=$row["pwd"];

			if($pwd===$passwd)			

				$flag=true;

		}

	}
	if($flag==true)
	{
		$sql="select * from sms_in where id='$id' and user_id='".$uid."' order by id desc LIMIT 1;";
		$result=$conn->query($sql);
		$sep="<sep>";
		if ($result->num_rows > 0) {		    
		    while($row = $result->fetch_assoc()) 
			{			
				echo "<sep2>".$row["id"]."$sep".$row["msg"]."$sep".$row["date_time_processed"];				
			}
		}
		else
			echo "empty";
	}
	else
		echo "Autorization failed";

}
else
	echo "No data suplied";
$conn->close();
?>