# E-Commerce_V1 Progress

## Languages
- Backend (Spring Boot)
- Frontend (HTML, Bootstrap, JS, Thymeleaf)
- Database (MongoDB)

## Modules
- Admin
- User

## Features
**Admin:**
- Login (with default admin credentials)✅
- Admin Dashboard✅
- Categories Management (Add, Update, Deactivate, Activate)✅
- Products Management (Add, Update, Deactivate, Activate)✅
- Orders Management (View, Update)
- Users Management (View, Update, Deactivate, Activate)
- Reviews Management (View, Update, Deactivate, Activate)
- Search by IDs

**User:**
- Register / Login✅
- User Dashboard
- Browse Categories/Products
- Cart Management (Add, Update, Remove)
- Wishlist Management (Add, Update, Remove)
- Address Management (Add, Update, Remove)
- Checkout
- Order Management (View, Update)
- Profile Management (View, Update, Deactivate)

## Models
**User:**✅
- id (String)
- name (String)
- email (String)
- password (String)
- phone (String)
- active (Boolean)

**Address:**✅
- id (String)
- userId (String)
- street (String)
- city (String)
- state (String)
- postalCode (String)
- country (String)
- mobile (String)
- active (Boolean)

**Category:**✅
- id (String)
- name (String)
- description (String)
- imageUrl (String)
- active (Boolean)

**Product:**✅
- id (String)
- categoryId (String)
- name (String)
- description (String)
- imageUrl (String)
- price (Double)
- MRP (Double)
- Quantity (Integer) - To be added
- active (Boolean)

**Wishlist:**✅
- id (String)
- userId (String)
- productIds (List of String)

**CartItem:**✅
- productId (String)
- quantity (Integer)

**Cart:**✅
- id (String)
- userId (String)
- cartItems (List of CartItem)

**Coupon:**✅
- id (String)
- code (String)
- discountPercentage (Double)
- maxDiscountAmount (Double)
- minOrderAmount (Double)
- expiryDate (Date)
- active (Boolean)

**OrderItem:**✅
- productId (String)
- quantity (Integer)
- price (Double)

**Order:**✅
- id (String)
- userId (String)
- orderItems (List of OrderItem)
- totalAmount (Double)
- status (String) Pending, Shipped, Delivered, Cancelled
- addressId (String)
- paymentMethod (String)
- couponCode (String)
- CouponAmount (String)
- orderDate (Date)
- updatedDate (Date)
- active (Boolean)

**Review:**✅
- id (String)
- userId (String)
- productId (String)
- rating (Integer)
- reviewText (String)
- reviewDate (Date) - To be added
- active (Boolean) - To be added
  
