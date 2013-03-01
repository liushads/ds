<#-- 宠物孵化 -->
<#include "/include/header.ftl">
<#include "/include/pet.ftl">
<card title="${game_title}"><p>
【${player_pet.customName}】开启技能需要花费：<br/>
${costCopper}铜或者${costGold/100}金<br/>
<@a href="/p?pe_OT/${pid}/${player_pet.id}/0"/>铜币开启</a><br/>
<@a href="/p?pe_OT/${pid}/${player_pet.id}/1"/>金票开启</a><br/>
<@a href="/p?pe_OT/${pid}/${player_pet.id}/2"/>金锭开启</a><br/>

<@gogame/>
</p></card>
</wml>