<?php
include('db.php');
// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error)
{
    die("Connection failed: " . $conn->connect_error);
}
$sql = "create table login
(
name VARCHAR(20) NOT NULL,
user VARCHAR(20) primary key,
pwd VARCHAR(20) NOT NULL,
email VARCHAR(40) UNIQUE NOT NULL,
img_path varchar(100) not null,
mobile DECIMAL(12) NOT NULL,
user_type varchar(20) default 'user'
);";

if ($conn->query($sql) === TRUE) {
    echo "table created successfully<br />";
}
//else{echo "Error: " . $sql . "<br>" . $conn->error;}

$name=$_POST["name"];
$user=$_POST["user"];
$password=$_POST["pwd1"];
$pwd=$_POST["pwd2"];
if($password!=$pwd)
{
	echo "<script>alert('password mismatch');window.location.assign('login_form.php#toregister');</script>";
}
else
{
$email=$_POST["email"];
$phno=$_POST["phno"];
$uploaddir = 'img/';//folder for upload
	if(strlen(basename($_FILES['fileToUpload']['name']))>0)
	{
		$uploadfile = $uploaddir . basename($_FILES['fileToUpload']['name']);//fullpath(directory+name)		
	}	
	else
	{
		$uploadfile = $uploaddir ."default.png";
		$default="default";
	}
	$format=pathinfo($uploadfile,PATHINFO_EXTENSION);//getting file extension
	if($format!="jpg" && $format!="jpeg" && $format!="png")//matching for format for calling appropriate function to the format upload
	{
			echo "<script>alert('invalid file format-$format');history.go(-1);</script>";
	}
	else
	{
		
		//$filename=basename( $_FILES["fileToUpload"]["name"]);//just file name	
		$sql="insert into login values('".$name."','".$user."','".$pwd."','".$email."','".$uploadfile."','".$phno."','user');";
		if ($conn->query($sql) === TRUE) 
		{
			if(move_uploaded_file($_FILES['fileToUpload']['tmp_name'], $uploadfile) || isset($default))
			{
				 echo "<script>alert('your account details has submitted. Now u can access sms services');window.location.assign('index.php');</script>";	
			} 
			else
			{
				echo "<script>alert('Possible file upload attack!');history.go(-1);</script>";
			}
		 
		}
		else {echo "<script>alert('enter details incorrectly or repeated!choose other username');history.go(-1);</script>";}
		//else {echo "Error: " . $sql . "<br>" . $conn->error;}
	}
}
$conn->close();
?> 