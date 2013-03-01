<#include "/include/header.ftl">
<card title="${game_title}"><p>
会员用户积分查询<br/>
<#if playerMember?exists>
我的积分：${player.rewardPoints}<br/>
我的成长值:${playerMember.memberLvValue}<br/>
<#else>
你还不是会员用户<br/>
</#if>
充值送积分<br/>
Q币充值1元积3积分,神州行充值1元积5积分,月卡会员每天固定增加1积分，年卡会员每天增加2积分<br/>
<br/>
<@goback/>
<@gogame/>
</p></card>
</wml>