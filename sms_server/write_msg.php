<?php
include('log_details.php');
include('db.php');
// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error)
{
    die("Connection failed: " . $conn->connect_error);
}
$sql="select * from login where user='".$_SESSION["user"]."';";
	$result=$conn->query($sql);
	if ($result->num_rows > 0) {
		$flag=false;
	    // output data of each row
	    while($row = $result->fetch_assoc()) 
		{
			$sender=$row["mobile"];
		}
	}
$conn->close();
?>
<!DOCTYPE html>
<html>
<head>
	<title>WRITE MESSAGE</title>	
	<?php include("links.php"); ?>
</head>
<body>
	<?php include("logout_opt.php"); ?>
	<br /><br />
	<h1>Compose SMS</h1>
	<form action="insert_sms.php" method="post">
		<?php
			if(isset($sender))
				echo "<input type='text' name='sender' value='$sender' readonly>";
			else
				echo "<input type='text' name='sender' placeholder='sender'>";
		?>
		<input type="text" name="msg" placeholder="msg">
		<input type="text" name="recipient" placeholder="recipient">
		<input type="submit" name="SUBMIT">
	</form>
</body>
</html>