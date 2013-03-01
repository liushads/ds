<#-- 技能传授成功 -->
<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#if sect_skill?exists>
	等待徒弟修炼${sect_skill.alias}.<br/>
<#else>
	<#if (err_msg?exists && err_msg.text?exists)>
	${err_msg.text}<br/>
	<#else>
	传授技能失败!<br/>
	</#if>
</#if>
<@a href="/p?co_LI/${pid}/${student_id}/"/>返回</a><br/>
<@gogame />
</p>
</card>
</wml>