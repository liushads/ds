<#include "/include/header.ftl">
<card title="${game_title}"><p>
	设置书签密码：<br/>
	1、为了防止书签被盗,可以设置保护密码，设置保护密码原保存的书签将全部失效。<br/>
	2、如果书签被盗,可以通过修改密码将之前设置的书签失效。<br/>
	3、第一次设置密码时候原密码可以为空<br/>
	原密码：<input type="text" name="password" value=""></input><br/>
	新密码：<input type="text" name="newPassword" value=""></input><br/>
	再一次：<input type="text" name="repetPassword" value=""></input><br/>
	<anchor>修改
		<@go href="/p?p_BPS/${pid}/" method="post" />
			<postfield name="1" value="0" />
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