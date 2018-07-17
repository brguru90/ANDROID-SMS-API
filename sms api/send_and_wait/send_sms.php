<?php
require_once("post.php");
$data=array();
$data["uid"]="guru";
$data["password"]="9611";
$data["pwd"]="9611";
$data["sender"]="9482399078";
$data["msg"]="hi5";
$data["recipient"]="9482399078";
$wait_sec=300;
echo send_sms($data,$wait_sec);
?>