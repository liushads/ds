<#include "/include/header.ftl">
<card title="${game_title}"><p>
	<#if desc?exists>
	${desc}<br/>
	</#if>
		修改帮会：帮会帮主才可以占领城市，占领城市可以享有占领城市的税收；城市越多帮会容纳人数越多。<br/>
		帮名：${tong.name}<br/>
		帮规：<input type="text" name="desc" value="${tong.description}" ></input><br/>
		<anchor>修改
		<@go href="/p?t_E/${pid}/" method="post"/>
			<postfield name="1" value="$desc" />
		</go>	
		</anchor><br/>
	<@goback/>
	<@gogame/>
	</p>
</card>
</wml>

