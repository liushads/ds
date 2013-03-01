<#include "/include/header.ftl">
<#include "/include/player.ftl">
<card title="${game_title}"><p>
会员成长值介绍:<br/>
<#if playerMember?exists>
身份：【${playerMember.memberLevelStr} 会员用户<br/>
我的成长值:${playerMember.memberLvValue}<br/>
</#if>
如果你会员到期了，那么你的LV成长值和积分都不再增加，每天会扣5的成长值减少。<br/>
如果以月为期限开通，每天10点成长值<br/>
如果以年为期限开通，每天15点成长值<br/>
会员成长体系目前包含6个阶段，分别用LV1至LV6表示<br/>
会员成长阶段 LV1 LV2 LV3 LV4 LV5 LV6 <br/>
成长值       0 600 1800 3600 6000 10800 <br/>
成长值（最低为0，次日显示成长值的变化。）
成长值按天增减<br/>
<@goback/>
<@gogame/>
</p></card>
</wml>