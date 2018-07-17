<?php
function send_sms($data,$sec){
	$url="http://guruinfo.6te.net/sms_server/insert_sms.php";
	$result=post($url,$data);
	if(preg_match("/^[0-9]+$/", $result))
	{
		$i=0;
		while($i<=$sec){
			$url='http://guruinfo.6te.net/sms_server/get_last_rec_sms.php';
			$data["id"]=$result;
			$res=post($url,$data);
			echo $i."<brr />";
			if($res!="empty")
				return $res;
			$i++;
			sleep(1);
		}
		return $res;
	}
	else
		return $result;
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