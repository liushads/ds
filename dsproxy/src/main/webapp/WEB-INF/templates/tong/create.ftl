<#include "/include/header.ftl">
<card title="${game_title}"><p>
	<#if desc?exists>
	${desc}<br/>
	</#if>
	创建帮会：帮主可以升级帮会，帮会达到一定等级，可以占领城市等。<br/>
	创建要求：等级40级以上，声望400以上。<br/>
	清理规则：对于创建时间大于3天，人数小于10人的帮会，系统自动撤销帮会<br/>
	帮名：<input type="text" name="name" value="" ></input><br/>
	帮规：<input type="text" name="desc" value="" ></input><br/>
	<anchor>创建
	<@go href="/p?t_Cr/${pid}/" method="post"/>
		<postfield name="1" value="$name" />
		<postfield name="2" value="$desc" />
	</go>		
	</anchor>
	<br/>
	<@goback/>
	<@gogame/>
	</p>
</card>
</wml>