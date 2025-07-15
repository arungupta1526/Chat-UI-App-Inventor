<div align="center">
<h1><kbd>🧩 ChatUI</kbd></h1>
An extension for MIT App Inventor 2.<br>
Extension component for ChatUI. Created using Rush.
</div>

## 📝 Specifications

---

🔎 **Use Place:** An extension for MIT App Inventor 2, Kodular, Niotron, Android Builder. <br>
👤 **Author:** Created by: [white_tiger](https://community.appinventor.mit.edu/u/white_tiger/summary) <br>
📦 **Package:** com.xtiger.chatui <br>
💾 **Size:** 19.26 KB <br>
⚙️ **Version:** 2 <br>
📱 **Minimum API Level:** 7 <br>
📅 **Updated On:** [date=2025-07-15 timezone="Asia/Calcutta"] <br>
💻 **Built & documented using:** [FAST](https://community.appinventor.mit.edu/t/fast-an-efficient-way-to-build-publish-extensions/129103?u=jewel) <small><mark>v3.8.1</mark></small> <br>
⬇️ **Aix:** [Download Link](./out/com.xtiger.chatui.aix) <br>

## <kbd>Total Blocks of Extension:</kbd>

![Total Blocks of Extension](./out/blocks/appinventor/ChatUI/blocks.png){width=90%} <br>

## <kbd>Events:</kbd>

**ChatUI** has total 2 events.

### 1. ProfilePictureClicked

![ProfilePictureClicked](./out/blocks/appinventor/ChatUI/ProfilePictureClicked_Event.png) <br>
Event raised when a profile picture is clicked

| Parameter | Type |
| --------- | ---- |
| name      | text |

### 2. MessageSelected

![MessageSelected](./out/blocks/appinventor/ChatUI/MessageSelected_Event.png) <br>
Event raised when a message is selected

| Parameter | Type   |
| --------- | ------ |
| message   | text   |
| index     | number |

## <kbd>Methods:</kbd>

**ChatUI** has total 13 methods.

### 1. Initialize

![Initialize](./out/blocks/appinventor/ChatUI/Initialize_Call_Method.png) <br>
Initialize the chat UI in a VerticalArrangement

| Parameter   | Type      |
| ----------- | --------- |
| arrangement | component |

### 2. Send

![Send](./out/blocks/appinventor/ChatUI/Send_Call_Method.png) <br>
Send a message

| Parameter  | Type |
| ---------- | ---- |
| message    | text |
| avatarUrl  | text |
| senderName | text |
| timestamp  | text |

### 3. Receive

![Receive](./out/blocks/appinventor/ChatUI/Receive_Call_Method.png) <br>
Receive a message

| Parameter    | Type |
| ------------ | ---- |
| message      | text |
| avatarUrl    | text |
| receiverName | text |
| timestamp    | text |

### 4. AddSubmessage

![AddSubmessage](./out/blocks/appinventor/ChatUI/AddSubmessage_Call_Method.png) <br>
Add a submessage to indicate someone joined the chat

| Parameter | Type |
| --------- | ---- |
| message   | text |

### 5. ShowTypingIndicator

![ShowTypingIndicator](./out/blocks/appinventor/ChatUI/ShowTypingIndicator_Call_Method.png) <br>
Show typing indicator

### 6. HideTypingIndicator

![HideTypingIndicator](./out/blocks/appinventor/ChatUI/HideTypingIndicator_Call_Method.png) <br>
Hide typing indicator

### 7. DeleteMessage

![ShowTypingIndicator](./out/blocks/appinventor/ChatUI/DeleteMessage_Call_Method.png) <br>
Delete a message by index (starting from 1)

| Parameter | Type   |
| --------- | ------ |
| index     | number |

### 8. EnableLinkDetection

![EnableLinkDetection](./out/blocks/appinventor/ChatUI/EnableLinkDetection_Call_Method.png) <br>
Enable or disable link detection in messages

| Parameter | Type    |
| --------- | ------- |
| enable    | boolean |

### 9. ClearSelection

![ClearSelection](./out/blocks/appinventor/ChatUI/ClearSelection_Call_Method.png) <br>
Clear all selected messages

### 10. GetSelectedCount

![GetSelectedCount](./out/blocks/appinventor/ChatUI/GetSelectedCount_Get_Method.png) <br>
Get the number of selected messages

- Return type: `number`

### 11. DeleteSelectedMessages

![DeleteSelectedMessages](./out/blocks/appinventor/ChatUI/DeleteSelectedMessages_Call_Method.png) <br>
Delete all selected messages

### 12. GetMessageCount

![GetMessageCount](./out/blocks/appinventor/ChatUI/GetMessageCount_Get_Method.png) <br>
Get the total number of messages

- Return type: `number`

### 13. ClearAllMessages

![ClearAllMessages](./out/blocks/appinventor/ChatUI/ClearAllMessages_Call_Method.png) <br>
Clear all messages

## <kbd>Setters:</kbd>

**ChatUI** has total 13 setter properties.

### 1. SentMessageColor

![SentMessageColor](./out/blocks/appinventor/ChatUI/SentMessageColor_Set_Property.png) <br>
Set the color for sent messages

- Input type: `number`

### 2. ReceivedMessageColor

![ReceivedMessageColor](./out/blocks/appinventor/ChatUI/ReceivedMessageColor_Set_Property.png) <br>
Set the color for received messages

- Input type: `number`

### 3. SentTextColor

![SentTextColor](./out/blocks/appinventor/ChatUI/SentTextColor_Set_Property.png) <br>
Set the text color for sent messages

- Input type: `number`

### 4. ReceivedTextColor

![ReceivedTextColor](./out/blocks/appinventor/ChatUI/ReceivedTextColor_Set_Property.png) <br>
Set the text color for received messages

- Input type: `number`

### 5. MessageCornerRadius

![MessageCornerRadius](./out/blocks/appinventor/ChatUI/MessageCornerRadius_Set_Property.png) <br>
Set the corner radius for message bubbles

- Input type: `number`

### 6. AvatarSize

![AvatarSize](./out/blocks/appinventor/ChatUI/AvatarSize_Set_Property.png) <br>
Set the size of avatar images

- Input type: `number`

### 7. MessageMaxWidth

![MessageMaxWidth](./out/blocks/appinventor/ChatUI/MessageMaxWidth_Set_Property.png) <br>
Set the maximum width of message bubbles

- Input type: `number`

### 8. MessageHorizontalPadding

![MessageHorizontalPadding](./out/blocks/appinventor/ChatUI/MessageHorizontalPadding_Set_Property.png) <br>
Set the horizontal padding of message bubbles

- Input type: `number`

### 9. MessageVerticalPadding

![MessageVerticalPadding](./out/blocks/appinventor/ChatUI/MessageVerticalPadding_Set_Property.png) <br>
Set the vertical padding of message bubbles

- Input type: `number`

### 10. ShowTimestamp

![ShowTimestamp](./out/blocks/appinventor/ChatUI/ShowTimestamp_Set_Property.png) <br>
Show or hide message timestamps

- Input type: `boolean`

### 11. ShowReadStatus

![ShowReadStatus](./out/blocks/appinventor/ChatUI/ShowReadStatus_Set_Property.png) <br>
Show or hide read status for sent messages

- Input type: `boolean`

### 12. FontFamily

![FontFamily](./out/blocks/appinventor/ChatUI/FontFamily_Set_Property.png) <br>
Set the font family for messages

- Input type: `text`

### 13. FontSize

![FontSize](./out/blocks/appinventor/ChatUI/FontSize_Set_Property.png) <br>
Set the font size for messages

- Input type: `number`
