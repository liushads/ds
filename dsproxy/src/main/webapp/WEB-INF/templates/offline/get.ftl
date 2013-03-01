<#include "/include/header.ftl">
<#include "/include/player.ftl">
<card title="${game_title}"><p>
我的闭关修炼<br/>
<#if expGetByPlayer?exists>
你已成功领取人物经验：${expGetByPlayer}<br/>
</#if>
<#if expGetByPet?exists>
你已成功领取宠物经验：${expGetByPet}.<br/>
</#if>
<#if inforPet?exists>
${inforPet}.<br/>
</#if>
你确定要领取闭关收益吗？<br/>
<@a href="/p?p_of/${pid}/gtc/1/"/>领取经验</a><br/>
闭关收益如下：<br/>
人物经验：${offlineExp} 宠物经验：${offlineExp}<br/>
离线经验池：${totalExp}<br/>
<@a href="/p?p_of/${pid}/gtc/2/"/>使用宠物修炼丹领取</a><br/>
<@a href="/p?p_of/${pid}/gtc/3/"/>使用人物修炼丹领取</a><br/>
<@goback/>
<@gogame/>
</p></card>
</wml>