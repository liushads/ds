<#include "/include/header.ftl">
<card title="${game_title}"><p>
	设置道具密码：<br/>
	1、为了防止道具被盗,可以设置保护密码，并将道具设定为锁定状态。<br/>
	2、被锁定的道具不能赠送，交易，卖出，只有解锁后才能进行这些操作<br/>
	3、第一次设置密码时候原密码可以为空<br/>
	原密码：<input type="text" name="password" value=""></input><br/>
	新密码：<input type="text" name="newPassword" value=""></input><br/>
	再一次：<input type="text" name="repetPassword" value=""></input><br/>
	<anchor>修改
		<@go href="/p?p_IPS/${pid}/" method="post" />
			<postfield name="1" value="1" />
			<postfield name="2" value="$password" />
			<postfield name="3" value="$newPassword" />
			<postfield name="4" value="$repetPassword" />
		</go>		
	</anchor>
	<br/>
	<@goback/>
	<@gogame/>
	</p>
</card>
</wml>