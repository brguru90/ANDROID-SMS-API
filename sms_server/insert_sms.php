<?php
include('db.php');
// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error)
{
    die("Connection failed: " . $conn->connect_error);
}

$flag=false;
if(!isset($_POST["uid"]) && !isset($_POST["password"]))
{
	include('log_details.php');
	$uid=$_SESSION["user"];	
	$flag=true;
}
else
{
	$uid=$_POST["uid"];
	$pwd=$_POST["password"];
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
}
if(isset($_POST["sender"]) && isset($_POST["msg"]) && isset($_POST["recipient"]) && $flag==true)
{
	$sender=$_POST["sender"];
	$msg=$_POST["msg"];
	$to=$_POST["recipient"];
	date_default_timezone_set("Asia/Calcutta"); 
	$sql="insert into sms_out (user_id,sender,msg,status,date_time_composed,recipient) values('$uid',$sender,'$msg','pending','".date("d/m/Y H:i:s")."',$to)";
	if ($conn->query($sql) === TRUE) 
	{
			$result=$conn->query("select * from sms_out where user_id='".$uid."' order by id desc LIMIT 1;");
			if ($result->num_rows > 0) {		    
		    while($row = $result->fetch_assoc()) 
			{			
				echo $row["id"];				
			}
		}
	}
	else
		echo "fail";
}
else
	echo "error";

$conn->close();
?>
