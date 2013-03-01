<#include "/include/header.ftl">
<card title="${game_title}"><p>
${location}<br/>
<#include "rewards.ftl">
${label_npc.name}：你已领取了${label_target}的九天皓令，在任意地点你都可以对${label_target}进行PK,小心….他可能就在你身边 
<br/>

<br/><@a href="/p?h/${pid}/list/${label_npc.id}/"/>返回</a>
<br/><@gofacility/>
</p>
</card>
</wml>
