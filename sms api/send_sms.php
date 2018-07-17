<?php
require_once("post.php");
$data["uid"]="guru";
$data["password"]="9611";
$data["sender"]="9482399078";
$data["msg"]="hi5";
$data["recipient"]="9482399078";
$url='http://guruinfo.6te.net/sms_server/insert_sms.php';

echo "ID=".post($url,$data);
?>