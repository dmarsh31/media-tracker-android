# Extra Credit Reflection — Design Alignment

*See `extra-credit-design-alignment.md` for submission requirements and the full assignment description.*

**Name: Dustin Marsh**
**Date: 6/26/2026**

---

## The Audit

*Before touching any code, compare your running app to the wireframes screen by screen. List what you found — be specific about which screen, which component, and what was different. "The colors were off" is not specific. "The active chip on the Search screen was using amber instead of primary container (#E0E0FF)" is specific.*

*List at least five concrete differences you found:*

1. The main logo and the container for it where the wrong color, they are using primary, and primary container. The logo its self seems to be its own unique color
    the container seems to be its own color as well (not one of the pre-defined colors in the wireframe doc)
2. All the input boxes corners are not rounded enough, for the login and sign up screens
3. In the login screen the "Don't have an account? Sign Up" text is wrong only the Sign-Up part is supposed to be colored
4. In the nav bar at the bottom, it's supposed to be white not this light gray color and, the highlighted tab isn't supposed to be an amber color it's supposed to be the blueish color
   which I believe is the primary and primary container
5. The cards on the search screen are a little too short and the whole text on the bottom of the card is gold when its only supposed to be the rating 

---

## What You Changed

*Walk through the changes you made. For each area of the design system, describe what the code looked like before and what you changed it to. Reference specific files and Composables.*

### Color System
I changed all the hex values in colors.kt to the ones specified in the design alignment, I also added 
Some new colors for the SearchComponets.kt (TV, Book, and Movie) and colors for progress (want to , in progress, finished)

<!-- What did your Color.kt look like before? What did you add or change? How did you wire colors into MaterialTheme? -->

### Typography
This one didn't need much work, but I changed a few weights in the type.kt file to align with the design alignment.md
besides that I changed the styles for many components such as the title of the app on the sign-in page and sign-up page.
I also changed the type in some of the components in the SearchComponents.kt

<!-- Were weights hardcoded? Did you update Type.kt? What specifically changed? -->

### Buttons
Most Buttons where already correct but some like the ones on the sign-in and sign-up page needed their rounded 
corners increased.

<!-- Which button variants needed work? What was wrong and how did you fix it? -->

### Text Fields
The default border color for text fields is already primary so that didn't need to change, but the rounded corners needed
to change for all my text buttons to match the wireframes

<!-- What shape and color changes did you make? -->

### Other Components

<!-- Chips, cards, bottom nav, status badges — what changed? -->

---

## What Was Hard

*Describe the most technically challenging part of this work. Don't write "it was confusing." Explain specifically what confused you, what you tried, and what helped you figure it out. If something in the Jetpack Compose theming system surprised you, describe it.*

---

## What You Understand Now

*What do you understand about Jetpack Compose theming — `MaterialTheme`, `colorScheme`, `typography`, component defaults — that you didn't fully grasp before this assignment? Be specific enough that you could explain it to a pod mate who hasn't done this yet.*

---

## Self-Assessment

*Look at the rubric (`extra-credit-design-alignment-rubric.md`) and estimate your own score for each section. Be honest — this does not affect your grade, but it shows me whether you read the rubric carefully.*

| Section | Possible | My Estimate |
|:---|:---:|:---:|
| Color System | 13 | |
| Typography | 5 | |
| Component Styling | 15 | |
| Navigation & Cards | 5 | |
| Reflection | 12 | |
| **Total** | **50** | |

*One thing I think I did well:*
I think that parts that I did style I did a good job at matching the colors and making the spacing between components match very closely to the wire frames.


*One thing I know I left incomplete or could have done better:*
for the sign-in and sign-up page, at the bottom where you can switch between the pages, in the wire frame you can only click "Sign Up" or "Log In" I
didn't figure out how to do that so the whole "Don't have an account? Sign Up" and "Already have an account? Log In" is a clickable button