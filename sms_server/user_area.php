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
$sql = "create table sms_out
(
id int NOT NULL AUTO_INCREMENT primary key,
user_id varchar(20) not null,
sender DECIMAL(12),
msg varchar(500) not null,
status varchar(10) not null,
date_time_composed varchar(20) not null,
date_time_processed varchar(20),
recipient DECIMAL(12) NOT NULL,
FOREIGN KEY (user_id) REFERENCES login(user)
);";
$conn->query($sql);
$sql = "create table sms_in
(
id int,
user_id varchar(20),
msg varchar(500) not null,
date_time_processed varchar(20),
primary key(id,user_id),
FOREIGN KEY (id) REFERENCES sms_out(id),
FOREIGN KEY (user_id) REFERENCES sms_out(user_id)
);";
$conn->query($sql);
$conn->close();
?>
<html>
<head>
	<title>SEND_SMS</title>
	<?php include("links.php"); ?>
	<style type="text/css">
		body *{font-size: 25px;color: blue;}
		table{width:500px;}
		.content h1{color: violet;font-size: 45px;}
		a{color: purple;}
		div{padding: 10px 10px 10px 10px;margin: 20px 20px 20px 20px;text-transform: capitalize;}
	</style>
</head>
<body>
	<?php include("logout_opt.php"); ?>
	<div class='content'>
		<table>
			<caption><h1>SEND SMS</h1></caption>
			<tr>
				<td><a href="sent_sms.php">View sent SMS</a></td>
				<td><a href="change_pwd.php">change password</a></td>
			</tr>
			<tr>
				<td colspan="2"><a href="write_msg.php">send SMS</a></td>
			</tr>
		</table>
	</div>
</body>
</html>