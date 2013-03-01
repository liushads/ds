<#include "/include/header.ftl">
<card title="${game_title}"><p>
${location}<br/>
${yuelao_npc.name}:<br/>
${friend.name}在${location}向你发出结婚请求，你是否接受?<br/>
<@a href="/p?pm_DP/${pid}/${friend.id}/1/${yuelao_npc.id}"/>同意</a><br/>
<@a href="/p?pm_DP/${pid}/${friend.id}/0/${yuelao_npc.id}"/>拒绝</a>
<br/>
<@goback />
<@gogame />
</p>
</card>
</wml>