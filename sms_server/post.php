<?php
function send_sms($data){
	$url="http://guruinfo.6te.net/sms_server/insert_sms.php";
	$result=post($url,$data);
	echo $result."<br />";
	if(preg_match("/^[0-9]+$/", $result))
	{
		echo $result;
		$i=0;
		while($i<=5){
			$url='http://guruinfo.6te.net/sms_server/get_last_sent_sms.php';
			$res=post($url,$data);
			echo $res;
			if($res!="empty")
				return $res;
			$i++;
			sleep(1);
		}
		return $res;
	}
	else
		echo $result;
}


function post($url,$data){
	$options = array(
	    'http' => array(
	        'header'  => "Content-type: application/x-www-form-urlencoded\r\n",
	        'method'  => 'POST',
	        'content' => http_build_query($data)
	    )
	);
	$context  = stream_context_create($options);
	$result = file_get_contents($url, false, $context);
	if ($result === FALSE) 
		return "failed";
	else
		return $result;
}

?>