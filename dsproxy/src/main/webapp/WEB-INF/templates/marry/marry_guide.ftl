<#include "/include/header.ftl">
<card title="${game_title}"><p>
${location}<br/>
${yuelao_npc.name}：千里姻缘一线牵<br/>
<@a href="/p?pm_IF/${pid}/${yuelao_npc.id}"/>了解结婚</a><br/>
<#if player_marry?exists && (player_marry.marryState &gt; 2)>
<@a href="/p?pm_AD/${pid}/"/>申请离婚</a><br/>
<#else>
<@a href="/p?pm_DL/${pid}/${yuelao_npc.id}"/>申请结婚</a><br/>
</#if>
<@a href="/p?pm_DL/${pid}/${yuelao_npc.id}"/>举办婚宴</a><br/>
<@a href="/p?pm_CL/${pid}/${yuelao_npc.id}"/>参加婚宴</a><br/>
<@gogame />
</p>
</card>
</wml>