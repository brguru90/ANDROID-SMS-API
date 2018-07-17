<?php
include('db.php');
// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error)
{
    die("Connection failed: " . $conn->connect_error);
}

if(isset($_POST["uid"]) && isset($_POST["pwd"]) && isset($_POST["data"]) && isset($_POST["type"]))
{

	$flag=false;
	$uid=$_POST["uid"];

	$pwd=$_POST["pwd"];

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
                
	        date_default_timezone_set("Asia/Calcutta"); 
		$data=explode("<sep>", $_POST["data"]);
		if($_POST["type"]=="sent")
		{
			$sql="update sms_out set date_time_processed='".date("d/m/Y H:i:s")."',status='sent',sender=".$data[1]." where id=".$data[0];
	                if ($conn->query($sql) === TRUE) 
				echo "sucess";
			else
				echo "fail";	
		}
		else 
		{
			$sql="insert into sms_in (id,user_id,msg,date_time_processed) values('$data[0]','$uid','$data[1]','".date("d/m/Y h:i:s")."')";
			if ($conn->query($sql) === TRUE) 
				echo "sucess";
			else
				echo "fail";	
		}
	}
	else
		echo "Autorization failed";

}
else
	echo "No data suplied";
$conn->close();
?>	