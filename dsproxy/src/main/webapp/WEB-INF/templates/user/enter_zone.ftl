<#include "/include/header.ftl">
<card title="${game_title}"><p>
<@app/>
<#if (my_players?exists) && (my_players?size>0)>
	选择角色进入游戏<br/>
	<#list my_players as p>
		<#if p.passwdBook?exists >
			<#assign userKey = p.passwdBook>
			<@a href="/p?p_EP/${p.id}/"/>${p.name}</a><br/>
		<#else>
			<#assign userKey = ""/>
			<@a href="/p?p_E/${p.id}/"/>${p.name}</a><br/>
		</#if>
	</#list><br/>
	<@a href="/p?u_CP/${uid}/"/>创建角色</a>
<#else>
<#include "/user/select_sect.ftl">
</#if>
<br/>

<a href="/fw.wml">服务条款</a><br/>
<@goback/>
<@qqbar/>
</p></card>
</wml>