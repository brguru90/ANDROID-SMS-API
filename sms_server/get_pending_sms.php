<?php
include('db.php');
// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error)
{
    die("Connection failed: " . $conn->connect_error);
}
if($test)
{
	$uid=$_POST["uid"]="guru";
	$pwd=$_POST["pwd"]="9611";
echo $uid.":".$pwd;
	$continue=true;
}
else
{
	if(isset($_POST["uid"]) && isset($_POST["pwd"]))
	{
		$uid=$_POST["uid"];
		$pwd=$_POST["pwd"];
		$continue=true;
	}
}

$flag=false;
if(isset($continue))
{
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
if($flag==true)
{
   	$json = array();
	$i=0;
	$sql="select * from sms_out where status='pending' and user_id='".$uid."';";
	if($test==2)
	{
		$result=$conn->query($sql);
		if ($result->num_rows > 0) {
		    // output data of each row
		    while($row = $result->fetch_assoc()) 
			{
				if($row["status"]==="pending")
				{				
					$json[$i]["from"]=$row["sender"];
					$json[$i]["msg"]=$row["msg"];
					$json[$i]["to"]=$row["recipient"];
					$i++;
				}
			}
			echo "{\n\t\"http://guruinfo.epizy.com\":\n\t\t";
			echo json_encode($json);
			echo "\n}";
			echo "\n\n<br /><br />\n\n";
		}
		else
			echo "empty";
	
		$result=$conn->query($sql);	
		$c=$result->num_rows;
		if ($result->num_rows > 0) {	    
			$str="{\n\t\"http://guruinfo.epizy.com\": [\n\t\t";
		    while($row = $result->fetch_assoc()) 
			{
				$c--;
				if($row["status"]==="pending")
				{				
					$str.="\n\t\t\t{";

					$str.="\n\t\t\t\t\"from\" : \"".$row["sender"]."\",";
					$str.="\n\t\t\t\t\"msg\" : \"".$row["msg"]."\",";
					$str.="\n\t\t\t\t\"to\" : \"".$row["recipient"]."\"";

					$str.="\n\t\t\t}";
					if($c>0) $str.=",";
				}
			}
			$str.="\n\t\t]\n}";
			echo $str;
		}
		else
			echo "empty";
		echo "<br /><br />\n";
	}
	$result=$conn->query($sql);
		$sep="<sep>";
		if ($result->num_rows > 0) {		    
		    while($row = $result->fetch_assoc()) 
			{			
				echo "<sep2>".$row["id"]."$sep".$row["sender"]."$sep".$row["msg"]."$sep".$row["recipient"]."\n";				
			}
		}
		else
			echo "empty";
}
else
	echo "authentication failure";
$conn->close();
?>