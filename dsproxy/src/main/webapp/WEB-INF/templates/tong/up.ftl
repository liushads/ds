<#include "/include/header.ftl">
<card title="${game_title}">
<p>
	<#if desc?exists>
	${desc}<br/>
	</#if>
	
	<#if tong_level?exists>
	帮会等级为：${tong.level}<br/>
	帮会威望为：${tong.fame}<br/>
	帮会资金为：${tong.copper}<br/>
	升级到下一级需要威望：${tong_level.needFame}<br/>
	升级到下一级需要金钱：${tong_level.upCopper}<br/>
	说明:${tong_level.msg}<br/>
	<@a href="/p?t_MU/${pid}/up/"/>升级</a><br/>
	</#if>
	<@goback/>
	<@gogame/>
</p>
</card>
</wml>

