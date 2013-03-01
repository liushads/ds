<#include "/include/header.ftl">
<card title="${game_title}"><p>
${location}<br/>
请选择和【${friend.name}】的婚礼类型:<br/>
婚礼开始后将自动发送系统通知，前来祝贺的玩家将获得精美礼包一份，半个小时后婚礼结束.成功举办婚宴后双方同时在线可以获得另一方获得经验的10%，攻击，防御，敏捷基础属性增加5%，此效果不与结婚成功加成叠加<br/>
豪华婚礼(20人)<@a href="/p?pm_MS/${pid}/2/${yuelao_npc.id}"/>举办</a><br/>
奢华婚礼(50人)<@a href="/p?pm_MS/${pid}/3/${yuelao_npc.id}"/>举办</a><br/>
说明:豪华婚宴需要豪华婚宴礼包;奢华婚宴需要奢宴婚礼礼包，礼包在商城购买<br/>
<@goback />
<@gogame />
</p>
</card>
</wml>