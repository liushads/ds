<#include "/include/header.ftl">
<card title="${game_title}"><p>
${last_npc.name}：<br/>
<#if desc?exists>
${desc}<br/>
<#else>
40级才能成为师傅,成为师傅之后<br/>
徒弟升级达到一定等级可以获得金<br/>
钱、师德奖励，徒弟通过师傅学技<br/>
能,师傅可以获得技能点。徒弟每 <br/>
升到10级,师傅可以获得固定的奖<br/>
励。<br/>
</#if>
<@a href="/p?co_BC/${pid}/"/>申请成为师傅</a><br/>
<@goback />
<@gofacility/>
</p></card>
</wml> 