<#include "/include/header.ftl">
<card title="${game_title}"><p>
${last_npc.name}：...<br/>
拜入师门之后徒弟可以额外将获得20%的打怪经验奖励,师傅获得5%打怪经验奖励.<br/>
如果和师傅同时在线则可获得增加5%的基础攻防奖励。<br/>
<#if (coaches?size > 0)>
	下面是师傅列表:<br/>
	<#list coaches as coach>
		<@a href="/p?co_BS/${pid}/${coach.playerId}/"/>拜师</a> 
		<@a href="/p?p_VO/${pid}/${coach.playerId}"/>${coach.name}(师德${coach.mark})</a><br/>		
	</#list>
<#else>
	师傅们都不在，请稍后再来。<br/>
</#if>
<@goback />
<@gofacility/>
</p></card>
</wml> 