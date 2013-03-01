<#include "/include/header.ftl">
<card title="${game_title}"><p>
<@a href="/p?p_LF/${pid}/0"/>好友</a>|<@a href="/p?p_LI/${pid}/0"/>亲友</a>|师徒<br/>
师徒功能请到月牙村点将台了解详情. <br/>
<#if player.student.status = 0>
你将拜入【<@a href="/p?p_VO/${pid}/${player.student.coachId}/"/>${player.student.coachName}</a>】门下，正在等待答复。<br/>
<@a href="/p?co_G/${pid}/"/>撤销请求</a><br/>
<#else>
我的师傅【<@a href="/p?p_VO/${pid}/${player.student.coachId}/"/>${player.student.coachName}</a>】，授业时间${player.student.teachTime}.<br/>
<#if (player.student.instructSkills?size > 0) ><#-- 有技能传授 -->
	师傅【<@a href="/p?p_VO/${pid}/${player.student.coachId}/"/>${player.student.coachName}</a>】传授技能：<br/>
	<#list player.student.instructSkills as skill>
		<@a href="/p?co_SAs/${pid}/${skill.id}/${skill.skillType}/"/>同意</a> 
		<@a href="/p?co_SRs/${pid}/${skill.id}/${skill.skillType}/"/>拒绝</a>
		${skill.alias}<br/>
	</#list>
</#if>
</#if>
<br/>
<@a href="/p?p_LF/${pid}/0"/>返回</a><br/>
<@gogame />
</p>
</card>
</wml>