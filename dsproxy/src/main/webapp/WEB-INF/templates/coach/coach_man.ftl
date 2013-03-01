<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#if ack_students?exists>
<#assign studentNum =ack_students?size />
<#else>
<#assign studentNum = 0/>
</#if>
<#assign quota = player.coach.quota - studentNum />
<#if (quota<0) >
	<#assign quota = 0 />
</#if>

<@a href="/p?p_LF/${pid}/0"/>好友</a>|<@a href="/p?p_LI/${pid}/0"/>亲友</a>|师徒<br/>
当前师德${player.coach.mark},<#if studentNum != 0>你现在有${studentNum}名徒弟<#else>你还没有招收徒弟</#if><#if quota != 0>,还可以招收${quota}名。<#else>.</#if><br/>
师徒功能请到月牙村点将台了解详情. <br/>
徒弟申请：<br/>
<#if wait_students?exists>
	<#list wait_students as student>		
		<@a href="/p?co_CA/${pid}/${student.playerId}"/>同意</a>
		<@a href="/p?co_CR/${pid}/${student.playerId}"/>拒绝</a>
		<@a href="/p?p_VO/${pid}/${student.playerId}"/>${student.name}</a><br/>
	</#list>
<#else>
	无申请<br/>
</#if>
我的徒弟:<br/>
<#if ack_students?exists>
	<#list ack_students as student>		
		<@a href="/p?co_LI/${pid}/${student.playerId}/"/>授业</a> 
		<@a href="/p?co_K/${pid}/${student.playerId}"/>逐出</a>		
		<@a href="/p?p_VO/${pid}/${student.playerId}"/>${student.name}</a>(授业${student.teachTime})<br/>
	</#list>
<#else>
	无徒弟<br/>
</#if>
<br/>
<@a href="/p?p_LF/${pid}/0"/>返回</a><br/>
<@gogame />
</p>
</card>
</wml>