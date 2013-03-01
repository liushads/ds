<#-- 显示玩家所有的宠物和宠物蛋 -->
<#include "/include/header.ftl">
<#include "/include/pet.ftl">
<card title="${game_title}"><p>
<@showPet player,player_pet/>
<@goback/>
<@gogame/>
</p></card>
</wml>